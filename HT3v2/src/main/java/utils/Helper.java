package utils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Helper {
    private String stringPage;

    public Helper() {
    }

    /*http://zetcode.com/articles/javareadwebpage/ - source of information*/
    public boolean open(String url, String timeout) {
        long time = Long.parseLong(timeout);
        //try {
        stringPage =
                readHtmlWithUrlAndBufferedReader(url);
                //readHtmlWithApacheHttpClient(url);
                //readHtmlWithJettyClient(url);
                //readHtmlWithHtmlCleaner(url);
                //readHtmlWithJsoup(url);
                //readHtmlWithWebClientAndHtmlPage(url);
                //readHtmlWithUrlAndInputStream(url);
        System.out.println(stringPage.replaceAll("\\n", ""));
        System.out.println("------------------------------------------------------");
        return true;
    }

    public String readHtmlWithHtmlCleaner(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CleanerProperties props = new CleanerProperties();
        props.setOmitXmlDeclaration(true);
        HtmlCleaner cleaner = new HtmlCleaner(props);
        TagNode node = null;
        try {
            node = cleaner.clean(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleHtmlSerializer htmlSerializer = new SimpleHtmlSerializer(props);
        StringWriter writer = new StringWriter();
        try {
            htmlSerializer.writeToStream(node, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String readHtmlWithApacheHttpClient(String stringUrl) {
        HttpGet request = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            request = new HttpGet(stringUrl);
            request.addHeader("User-Agent", "Apache HTTPClient");
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            return content;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
        return "";
    }

    public String readHtmlWithJettyClient(String stringUrl) {
        org.eclipse.jetty.client.HttpClient client = null;
        try {
            client = new org.eclipse.jetty.client.HttpClient();
            client.start();
            org.eclipse.jetty.client.api.ContentResponse res = client.GET(stringUrl);
            return res.getContentAsString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public String readHtmlWithWebClientAndHtmlPage(String stringUrl) {
        try (WebClient webClient = new WebClient()) {
            HtmlPage page = webClient.getPage(stringUrl);
            WebResponse response = page.getWebResponse();
            String content = response.getContentAsString();
            return content;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String readHtmlWithJsoup(String StringUrl) {
        String html = "";
        try {
            html = Jsoup.connect(StringUrl).get().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    public String readHtmlWithUrlAndBufferedReader(String stringUrl) {
        BufferedReader br = null;
        try {
            URL url = new URL(stringUrl);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            if (stringUrl.contains("google.com")){
                byte[] bytes = sb.toString().getBytes("UTF-8");
                return new String(bytes, "CP1251");
            } else {
                return sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private String readHtmlWithUrlAndInputStream(String address) {
        URL url = null;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream reader = null;
        try {
            reader = new BufferedInputStream(url.openConnection().getInputStream());
            Thread.sleep(10000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BufferedOutputStream outputStream = new BufferedOutputStream(output);
        try {
            output.write(reader.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
            output.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = null;
        try {
            result = new String(output.toByteArray(), "Cp1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkLinkPresentByHref(String href) {
        return stringPage.contains("href=\"" + href + "\"");
    }

    public boolean checkLinkPresentByName(String linkName) {
        return stringPage.contains("<a\\.*name=\"" +linkName + "\"");
    }

    public boolean checkPageTitle(String title) {
        return stringPage.contains("<title>" + title + "<");
    }

    public boolean checkPageContains(String text) throws IOException {
        return stringPage.contains(">" +text + "<");
    }
    public ArrayList<String> readerLines(String file) throws IOException {
        ArrayList<String> commands = new ArrayList<String>();
        Path path = Paths.get(file);
        if (Files.exists(path)) {
            commands.addAll(Files.readAllLines(path));
        }
        return commands;
    }

    public String[] parser(String line) {
        String[] parameters = line.split("\\\"?\\s?\\\"");
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = parameters[i].trim();
        }
        return parameters;
    }

    public boolean execute(String[] arguments) {
        switch (arguments[0]) {
            case "open":
                return open(arguments[1], arguments[2]);
            case "checkLinkPresentByHref":
                return checkLinkPresentByHref(arguments[1]);
            case "checkLinkPresentByName":
                return checkLinkPresentByName(arguments[1]);
            case "checkPageTitle":
                return checkPageTitle(arguments[1]);
            case "checkPageContains":
                try {
                    return checkPageContains(arguments[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            default:
                return false;
        }
    }
}
