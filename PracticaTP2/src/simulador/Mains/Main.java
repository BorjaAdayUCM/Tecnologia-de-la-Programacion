package simulador.Mains;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import simulador.Controlador.Controlador;
import simulador.Modelo.SimuladorTrafico;
import simulador.Swing.VentanaPrincipal;
import simulador.Utilidades.Utils;

public class Main {

	private enum ModoEjecucion 
	{
		BATCH("batch"), 
		GUI("gui");
		
		private String modo;
		
		private ModoEjecucion(String modo) 
		{
			this.modo = modo;
		}
		
		public static ModoEjecucion parse(String param)
		{
			for (ModoEjecucion modo : ModoEjecucion.values()) 
			{
				if (modo.modo.equalsIgnoreCase(param))
				{
					return modo;
				}
			}
			return null;
		}
	}
	
	private final static Integer limiteTiempoPorDefecto = 10;
	private static Integer limiteTiempo = null;
	private static String ficheroEntrada = null;
	private static String ficheroSalida = null;
	private static ModoEjecucion modo = null;
	
	private static void ParseaArgumentos(String[] args) {

		// define the valid command line options
		//
		Options opcionesLineaComandos = Main.construyeOpciones();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine linea = parser.parse(opcionesLineaComandos, args);
			parseaOpcionHELP(linea, opcionesLineaComandos);
			parseaOpcionModo(linea);
			parseaOpcionFicheroIN(linea);
			parseaOpcionFicheroOUT(linea);
			parseaOpcionSTEPS(linea);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] resto = linea.getArgs();
			if (resto.length > 0) {
				String error = "Illegal arguments:";
				for (String o : resto)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options construyeOpciones() {
		Options opcionesLineacomandos = new Options();
		opcionesLineacomandos.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Selecciona modo de ejecucion.").build());
		opcionesLineacomandos.addOption(Option.builder("h").longOpt("help").desc("Muestra la ayuda.").build());
		opcionesLineacomandos.addOption(Option.builder("i").longOpt("input").hasArg().desc("Fichero de entrada de eventos.").build());
		opcionesLineacomandos.addOption(Option.builder("o").longOpt("output").hasArg().desc("Fichero de salida, donde se escriben los informes.").build());
		opcionesLineacomandos.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Pasos que ejecuta el simulador en su bucle principal (el valor por defecto es " + Main.limiteTiempoPorDefecto + ").").build());
		return opcionesLineacomandos;
	}

	private static void parseaOpcionHELP(CommandLine linea, Options opcionesLineaComandos) {
		if (linea.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), opcionesLineaComandos, true);
			System.exit(0);
		}
	}

	private static void parseaOpcionFicheroIN(CommandLine linea) throws ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
	}

	private static void parseaOpcionFicheroOUT(CommandLine linea) throws ParseException {
		Main.ficheroSalida = linea.getOptionValue("o");
	}

	private static void parseaOpcionSTEPS(CommandLine linea) throws ParseException {
		String t = linea.getOptionValue("t", Main.limiteTiempoPorDefecto.toString());
		try {
			Main.limiteTiempo = Integer.parseInt(t);
			assert (Main.limiteTiempo < 0);
		} catch (Exception e) {
			throw new ParseException("Valor invalido para el limite de tiempo: " + t +  ".");
		}
	}
	
	private static void parseaOpcionModo(CommandLine linea) throws ParseException {
		if (linea.hasOption("m")) {
			String t = linea.getOptionValue("m", "batch");
			Main.modo = ModoEjecucion.parse(t);
			if (Main.modo == null) throw new ParseException("Valor invalido para el modo de ejecucion.");
		}
		else Main.modo = ModoEjecucion.BATCH;
	}

	private static void iniciaModoGrafico() throws FileNotFoundException, InvocationTargetException, InterruptedException 
	{
		SimuladorTrafico sim = new SimuladorTrafico(); 
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida)); 
		Controlador ctrl = new Controlador(sim, Main.limiteTiempo, null, os);
		SwingUtilities.invokeLater(new Runnable() 
		{ 
			@Override
			public void run() 
			{ 
				try 
				{
					new VentanaPrincipal(Main.ficheroEntrada, ctrl);
				} 
				catch (Exception e)
				{
					if (e instanceof NumberFormatException) Utils.dialogoError("Se esperaba un numero en la conversion.");
					else if (e instanceof IOException) Utils.dialogoError("Error en la lectura de eventos.");
					else Utils.dialogoError(e.getMessage());
					ctrl.reinicia();
				}
			}
		});
	}
	
	private static void iniciaModoEstandar() throws Exception {
		InputStream is = new FileInputStream(new File(Main.ficheroEntrada));
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		SimuladorTrafico sim = new SimuladorTrafico();
		Controlador ctrl = new Controlador(sim,Main.limiteTiempo,is,os);
		try
		{
			ctrl.ejecuta();
			is.close();
			System.out.println("Done!");
		}
		catch (Exception e)
		{
			if (e instanceof NumberFormatException) System.out.print("Se esperaba un numero en la conversion.");
			else if (e instanceof IOException) System.out.print("Error de la lectura de eventos.");
			else System.out.print(e.getMessage());
			System.out.println(" Se ha terminado el programa generando resultados vacíos.");
		}
	}
	
	public static void ejecutaFicheros(String path) throws Exception {
		File dir = new File(path);
		if (!dir.exists()) {
			throw new FileNotFoundException(path);
		}
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});
		for (File file : files) {
			Main.ficheroEntrada = file.getAbsolutePath();
			System.out.println(Main.ficheroEntrada);
			Main.ficheroSalida = file.getAbsolutePath() + ".out";
			if (file.getAbsolutePath().equalsIgnoreCase("C:\\Users\\borja\\Desktop\\Programas de Desarrollo\\EclipsePhoton\\PracticaTP2\\Comprobaciones\\personal1.ini"))
				Main.limiteTiempo = 30;
			else if (file.getAbsolutePath().equalsIgnoreCase("C:\\Users\\borja\\Desktop\\Programas de Desarrollo\\EclipsePhoton\\PracticaTP2\\Comprobaciones\\personal2.ini"))
				Main.limiteTiempo = 120;
			else
				Main.limiteTiempo = 10;
			Main.iniciaModoEstandar();
		}
	}

	public static void main(String[] args) throws Exception {
		Main.ParseaArgumentos(args);
		switch (Main.modo)
		{
		case GUI: Main.iniciaModoGrafico(); break;
		case BATCH: Main.iniciaModoEstandar(); break;
		}
	}

}
