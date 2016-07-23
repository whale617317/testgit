package day_060721;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JianKangXuQiu{
	public static Collection<String> UrlRegex(String targetStr, String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(targetStr);
		Collection<String> col = new ArrayList<String>();
		while (matcher.find()) {
			// col.add("http://als.ilifeb.com/goods"+ matcher.group(2));
			col.add(matcher.group(3));
		}
		return col;
	}

	public static String RegexString(String targetStr, String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(targetStr);	 
		while(matcher.find()) {
			return(matcher.group(2));
		}
		return "null";
	}
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		WebClient  webClient=new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		
		int i = 1;
		while(i<13){
			//HtmlPage page=webClient.getPage("http://www.yapingguo.com/list/300000348-p"+i+".html");//健康需求14
			//HtmlPage page=webClient.getPage("http://www.yapingguo.com/list/300000003-p"+i+".html");//传统滋补4
			//HtmlPage page=webClient.getPage("http://www.yapingguo.com/list/300000058-p"+i+".html");//膳食营养14
			//HtmlPage page=webClient.getPage("http://www.yapingguo.com/list/300000425-p"+i+".html");//健康品4
			HtmlPage page=webClient.getPage("http://www.yapingguo.com/list/300000432-p"+i+".html");//人群健康12
			i++;
			java.util.List<HtmlAnchor> achList=page.getAnchors();
			for(HtmlAnchor ah:achList){
				//System.out.println(ah.getHrefAttribute());
				Collection<String> Url = UrlRegex(
						ah.getHrefAttribute(),
						"(http://www\\.yapingguo)(:?\\.[hk|com]+/item/)(\\d+)");
				for(String newah:Url){
					HtmlPage page3=webClient.getPage("http://wa.yapingguo.com/comment/home/datalist?item="+newah+"&pageNumber=1&c=f&callback=callback");
					String str = page3.asText();
					String str2 = RegexString(str,"(（)(\\d+)(）)");
					System.out.println(str2);
				}
			}
		}
	}
}















