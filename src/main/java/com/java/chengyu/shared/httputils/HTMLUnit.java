package com.java.chengyu.shared.httputils;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class HTMLUnit
{
   static final Logger FUNCTION = Logger.getLogger("FUNCTION");
   
   static final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
   
   String baseUrl;
   HtmlPage mainPage;
   
   public HTMLUnit(String baseUrl)
   {
      this.baseUrl = baseUrl;
      try
      {
         mainPage = webClient.getPage(baseUrl);
      }
      catch (Exception e)
      {
         FUNCTION.error("Get main page failed : " + baseUrl);
      }
   }
   
   static
   {
      webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
      webClient.getOptions().setJavaScriptEnabled(false);
      webClient.getOptions().setUseInsecureSSL(true);
      webClient.getOptions().setCssEnabled(false);
      webClient.getOptions().setRedirectEnabled(false);
      webClient.getOptions().setSSLClientProtocols(new String[] { "TLSv1.2", "TLSv1.1" });
   }
   
   public String searchFromSearchEngine(String queryParam, String inputXpath, String submitXpath, String resultXpath)
   {
      try
      {
         HtmlInput input = (HtmlInput) mainPage.getFirstByXPath(inputXpath);
         input.setValueAttribute(queryParam);
         
         HtmlSubmitInput submit = (HtmlSubmitInput) mainPage.getFirstByXPath(submitXpath);
         HtmlPage result = submit.click();
//         FUNCTION.info(result.asXml());
         DomElement element = result.getFirstByXPath(resultXpath);
         if (element != null)
            return element.getTextContent();
         return null;
      }
      catch (Exception e)
      {
         FUNCTION.error("Search " + queryParam + " From " + baseUrl + " Failed !");
         return null;
      }
      
   }
   
   public static String getHTMLPageFromUrl(String url, String xpath)
   {
      try
      {
         HtmlPage mainPage = webClient.getPage(url);
         DomElement element = mainPage.getFirstByXPath(xpath);
         if (element != null)
            return element.getTextContent();
         return null;
      }
      catch (Exception e)
      {
         FUNCTION.error("Download from " + url + " Failed !");
         return null;
      }
      
   }
}
