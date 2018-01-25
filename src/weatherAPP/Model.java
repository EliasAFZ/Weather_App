package weatherAPP;

import java.net.URL;
import java.nio.charset.Charset;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  models all weather logic
 * 
 * @author Elias
 */
public class Model {

    private String startKey = "http://api.wunderground.com/api";
    private String apiKey = "/b66e20077e71abb8/";
    private String midKey = "conditions/q/";
    private String endKey = ".json";
    private String zipCode;
    private String city;
    private String icon;
    private URL url; 
    private BufferedImage x;
    private ImageIcon image;
    private String cloudCoverage;
    private String currentTemp;
    private String windSpeed;
    private String windDir;
    private String humidity;
    private String precip;
    
    
    /**
     *  constructor for weather takes gui input and calls method to parse through it
     * 
     * @param _zipcode
     * @param _city
     * @throws JSONException
     * @throws IOException 
     */
    public Model(String _zipcode, String _city) throws JSONException, IOException {
        this.zipCode = _zipcode;
        this.city = _city;
        this.weatherSum();
    }

    /**
     *  helper method for parsing json
     * 
     * @param scan
     * @return
     * @throws IOException 
     */
    private String parseUrl(Reader scan) throws IOException {
        StringBuilder url = new StringBuilder();
        int pos;
        while ((pos = scan.read()) != -1) {
            url.append((char) pos);
        }
        return url.toString();
    }

    /**
     *  parses json
     * 
     * @param url
     * @return
     * @throws JSONException
     * @throws IOException 
     */
    private JSONObject scanJsonUrl(String url) throws JSONException, IOException {
        try (InputStream stream = new URL(url).openStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            String text = parseUrl(br);
            JSONObject jo = new JSONObject(text);
            return jo;
        }
    }

    /**
     *  calls and sets all weather variables
     * 
     * @throws JSONException
     * @throws IOException 
     */
    public void weatherSum() throws JSONException, IOException{
        JSONObject js = scanJsonUrl(startKey + apiKey + midKey + zipCode + "/" + city + endKey);
        js = (JSONObject) js.get("current_observation");
        icon =  (String) js.get("icon_url");
        url = new URL(icon);
        x = ImageIO.read(url);
        image = new ImageIcon(x);
        cloudCoverage = (String) js.get("weather");
        currentTemp = js.get("temp_f") + " F";
        windSpeed = js.get("wind_mph") + " MPH";
        windDir = (String) js.get("wind_dir");
        humidity = (String) js.get("relative_humidity");
        precip = (String) js.get("precip_today_metric") + " %";
        
    }
    
    /**
     *  
     * @return 
     */
    public ImageIcon getImage(){
        return image;
    }
    /**
     * 
     * @return 
     */
    public String getCloud(){
        return cloudCoverage;
    }
    /**
     * 
     * @return 
     */
    public String getLocation(){
        return city;
    }
    /**
     * 
     * @return 
     */
    public String getTemp(){
        return currentTemp;
    }
    /**
     * 
     * @return 
     */
    public String getWindSpeed(){
        return windSpeed;
    }
    /**
     * 
     * @return 
     */
    public String getWindDir(){
        return windDir;
    }
    /**
     * 
     * @return 
     */
    public String getHum(){
        return humidity;
    }
    /**
     * 
     * @return 
     */
    public String getPrecip(){
        return precip;
    }
}
