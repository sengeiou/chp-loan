
package com.creditharmony.loan.webservice.infodisclosure.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.creditharmony.loan.webservice.infodisclosure.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetDetailInfo_QNAME = new QName("http://service.infodisclosure.webservice.loan.creditharmony.com/", "getDetailInfo");
    private final static QName _GetDetailInfoResponse_QNAME = new QName("http://service.infodisclosure.webservice.loan.creditharmony.com/", "getDetailInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.creditharmony.loan.webservice.infodisclosure.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDetailInfo }
     * 
     */
    public GetDetailInfo createGetDetailInfo() {
        return new GetDetailInfo();
    }

    /**
     * Create an instance of {@link GetDetailInfoResponse }
     * 
     */
    public GetDetailInfoResponse createGetDetailInfoResponse() {
        return new GetDetailInfoResponse();
    }

    /**
     * Create an instance of {@link InfoDisclosure }
     * 
     */
    public InfoDisclosure createInfoDisclosure() {
        return new InfoDisclosure();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDetailInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.infodisclosure.webservice.loan.creditharmony.com/", name = "getDetailInfo")
    public JAXBElement<GetDetailInfo> createGetDetailInfo(GetDetailInfo value) {
        return new JAXBElement<GetDetailInfo>(_GetDetailInfo_QNAME, GetDetailInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDetailInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.infodisclosure.webservice.loan.creditharmony.com/", name = "getDetailInfoResponse")
    public JAXBElement<GetDetailInfoResponse> createGetDetailInfoResponse(GetDetailInfoResponse value) {
        return new JAXBElement<GetDetailInfoResponse>(_GetDetailInfoResponse_QNAME, GetDetailInfoResponse.class, null, value);
    }

}
