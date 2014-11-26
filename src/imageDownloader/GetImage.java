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
 *
 * SwingWorker class to represent the task of identifying the images in the given webpage and downloading them.
 */
public class GetImage extends SwingWorker<Void, Void> {
    //Set fields
    private String url;
    private String types;
    private DefaultTableModel results;
    private int threadNo;
    private int maxImg;
    private JProgressBar progressBar;
    private JLabel lblImage;

    /**
     * Constructor for the GetImage class
     *
     * @param site URL for site to download images
     * @param type type of images the user wishes to download
     * @param table the table to be displayed on the GUI
     * @param thread number of preferred threads to download images with
     * @param maxImage max number of images to download
     * @param bar the progress bar
     * @param label label for the progress bar
     */
    public GetImage(String site, String type, DefaultTableModel table, int thread, int maxImage, JProgressBar bar, JLabel label) {

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
        progressBar = bar;
        lblImage = label;
    }

    /**
     * Grabs the images using JSoup and then initializes a thread pool to submit 4
     * image downloading images.
     *
     * @return null
     * @throws Exception
     */
    @Override
    protected Void doInBackground() throws Exception {
        Document doc;
        try {
            //Get all images
            doc = Jsoup.connect(url).get();
            //Selector uses CSS selector with regular expression
            Elements images = doc.select("img[src~=(?i)\\.(" + types + ")]");
            int size;
            if (images.size() > maxImg) {
                size = maxImg;
            } else {
                size = images.size();
            }

            //Set the progress bar bounds
            progressBar.setMinimum(0);
            progressBar.setMaximum(size);

            //Fill an array with the image URLS
            String[] imgUrlJobs = new String[size];
            int x = 0;
            for (Element image : images) {
                if (x >= maxImg) break;
                String urlstr = image.attr("src");
                //Deal with images who's sources are relative
                if (urlstr.substring(0, 2).equals("//")) urlstr = "http:" + urlstr;
                if (!urlstr.substring(0, 7).equals("http://"))
                    urlstr = "http://" + new URL(url).getHost().toString() + urlstr;
                imgUrlJobs[x] = urlstr;
                x++;
                //Open a URL Stream
            }

            lblImage.setVisible(true);

            //Initialise a new thread pool of a fixed size
            ExecutorService pool = Executors.newFixedThreadPool(threadNo);
            x = 1;
            for (String s : imgUrlJobs) {
                //Create a new runnable object for each image download and give these jobs to the thread pool to carry out.
                pool.execute(new ImageGetter(s, size));
                x++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occured: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Runnable class to represent the job of downloading a single image
     */
    private class ImageGetter implements Runnable {

        private String urlDownload;
        private int noOfImages;

        /**
         *
         * @param url of the image to download
         * @param noOfImages number of images being downloading
         */
        public ImageGetter(String url, int noOfImages) {
            urlDownload = url;
            this.noOfImages = noOfImages;
        }

        /**
         * Mandatory implementation of the run method for the Runnable object
         * Defines how to download the image, update the progress bar and add the image to the table.
         */
        @Override
        public void run() {
            try {
                URL url = new URL(urlDownload);
                HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
                //Get the filesize of the image and set the property for the table
                long fileSize = httpConnection.getContentLength();
                String fileSizeString;
                if (fileSize > 1000) {
                    fileSizeString = fileSize / 1000 + " Kb";
                } else {
                    fileSizeString = fileSize + " bytes";
                }
                //Get the filename for the image
                String fileName = urlDownload.substring(urlDownload.lastIndexOf('/') + 1, urlDownload.length());
                //Read the image into a buffered image
                BufferedImage img = ImageIO.read(url);
                //Get the resolution of the image.
                int width = img.getWidth();
                int height = img.getHeight();
                //Add to the table
                results.addRow(new Object[]{img, fileName, fileSizeString, width + "x" + height});
                //Update progress bar
                progressBar.setValue(progressBar.getValue() + 1);
                lblImage.setText("Downloading image " + progressBar.getValue() + " of " + noOfImages);
            } catch (IOException e) {
                lblImage.setText("Error occured: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}



