package imageDownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chris on 20/11/14.
 */
public class GetImage extends SwingWorker<Void, Void> {

    private String url;
    private String types;
    private DefaultTableModel results;
    private int threadNo;
    private int maxImg;

    public GetImage(String site, String type, DefaultTableModel table, int thread, int maxImage) {

        url = site;
        if (type == "All") {
            types = "png|jpe?g|gif";
        } else if (type == "jpeg") {
            types = "jpe?g";
        } else {
            types = type;
        }
        results = table;
        threadNo = thread;
        maxImg = maxImage;
    }

    @Override
    protected Void doInBackground() throws Exception {
        Document doc;
        try {
            //get all images
            doc = Jsoup.connect(url).get();
            // selector uses CSS selector with regular expression
            Elements images = doc.select("img[src~=(?i)\\.(" + types + ")]");
            int size;
            if (images.size() > maxImg) {
                size = maxImg;
            } else {
                size = images.size();
            }

            String[] imgUrlJobs = new String[size];
            int x = 0;
            for (Element image : images) {
                if (x >= maxImg) break;
                String urlstr = image.attr("src");
                if (urlstr.substring(0, 2).equals("//")) urlstr = "http:" + urlstr;
                if (!urlstr.substring(0, 7).equals("http://"))
                    urlstr = "http://" + new URL(url).getHost().toString() + urlstr;
                imgUrlJobs[x] = urlstr;
                x++;
                //Open a URL Stream
            }

            ExecutorService pool = Executors.newFixedThreadPool(threadNo);
            for (String s : imgUrlJobs) {
                pool.execute(new ImageGetter(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class ImageGetter implements Runnable {

        private String urlDownload;

        public ImageGetter(String url) {
            urlDownload = url;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(urlDownload);
                HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
                long fileSize = httpConnection.getContentLength();
                System.out.println(fileSize + "");
                String fileName = urlDownload.substring(urlDownload.lastIndexOf('/') + 1, urlDownload.length());
                BufferedImage img = ImageIO.read(url);
                int width = img.getWidth();
                int height = img.getHeight();
                results.addRow(new Object[]{img, fileName, fileSize + " bytes", width + "x" + height});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


/* InputStream is = url.openStream();
    OutputStream os = new FileOutputStream(destName);

	byte[] b = new byte[2048];
	int length;

	while ((length = is.read(b)) != -1) {
		os.write(b, 0, length);
	}

	is.close();
	os.close();
	*/
