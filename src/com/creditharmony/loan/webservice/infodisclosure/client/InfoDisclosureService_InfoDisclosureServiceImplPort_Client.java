
package com.creditharmony.loan.webservice.infodisclosure.client;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.6
 * 2016-07-14T10:01:30.428+08:00
 * Generated source version: 3.1.6
 * 
 */
public final class InfoDisclosureService_InfoDisclosureServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://service.infodisclosure.webservice.loan.creditharmony.com/", "InfoDisclosureServiceImplService");

    private InfoDisclosureService_InfoDisclosureServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = InfoDisclosureServiceImplService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        InfoDisclosureServiceImplService ss = new InfoDisclosureServiceImplService(wsdlURL, SERVICE_NAME);
        InfoDisclosureService port = ss.getInfoDisclosureServiceImplPort();  
        
        {
        System.out.println("Invoking getDetailInfo...");
        java.lang.String _getDetailInfo_contractCode = "";
        com.creditharmony.loan.webservice.infodisclosure.client.InfoDisclosure _getDetailInfo__return = port.getDetailInfo(_getDetailInfo_contractCode);
        System.out.println("getDetailInfo.result=" + _getDetailInfo__return);


        }

        System.exit(0);
    }

}
