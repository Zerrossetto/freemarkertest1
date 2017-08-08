package eu.p4cards.lorenzo.freemarkertest1;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class App {
	
	public static void main(String[] args) {
		
		Configuration cfg = generateConfiguration();
		Template template = null;

		try {
			template = cfg.getTemplate("template1.ftlh");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("subject", "mondo");

		printToConsole(template, model);
	}

	private static Configuration generateConfiguration() {

		// should be a singleton
		Configuration cfg;
		URL templateFolder;
		FileTemplateLoader ftl = null;

		cfg = new Configuration(Configuration.VERSION_2_3_26);
		templateFolder = ClassLoader.getSystemResource("freemarker/templates");

		try {
			ftl = new FileTemplateLoader(new File(templateFolder.toURI()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		if (ftl == null)
			return null;
		else
			cfg.setTemplateLoader(ftl);
		
		cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

		return cfg;
	}

	@SuppressWarnings("unused")
	private static void printToConsole(Template template) {
		printToConsole(template, new HashMap<>());
	}

	private static void printToConsole(Template template, Map<String, Object> model) {

		try (Writer console = new OutputStreamWriter(System.out)) {
			template.process(model, console);
			console.flush();
		} catch (TemplateException | IOException e) {
			e.printStackTrace();
		}
	}
}
