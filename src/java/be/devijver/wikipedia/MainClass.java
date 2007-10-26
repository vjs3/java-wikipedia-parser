package be.devijver.wikipedia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class MainClass {

	public static void main(String[] args) throws ParseException, IOException {
		Options options = new Options();

		options.addOption("f", true, "Wikitext file to parse");
		options.addOption("o", true, "HTML file to write");

		CommandLineParser parser = new PosixParser();

		CommandLine cmdLine = parser.parse(options, args);

		String fileName = cmdLine.getOptionValue("f");

		Reader reader = new FileReader(fileName);

		BufferedReader buf = new BufferedReader(reader);

		String wikitext = "";
		String line;
		while ((line = buf.readLine()) != null) {
			wikitext += line + "\n";
		}

		buf.close();

		Writer out;
		if (cmdLine.getOptionValue("o") != null) {
			out = new FileWriter(cmdLine.getOptionValue("o"), false);
		} else {
			out = new OutputStreamWriter(System.out);
		}

		try {
			Parser.toHtml(wikitext, null, out, true);
		} finally {
			out.close();
		}
	}
}
