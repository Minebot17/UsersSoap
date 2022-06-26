package userssoap.config;

import jdk.internal.org.xml.sax.SAXParseException;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.xml.sax.SAXException;
import userssoap.model.Error;
import userssoap.model.OperationResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomValidationInterceptor extends PayloadValidatingInterceptor {

    @Override
    protected boolean handleRequestValidationErrors(
            MessageContext messageContext,
            org.xml.sax.SAXParseException[] errors)
            throws TransformerException {

        JAXBContext jaxbContext;
        StringWriter stringWriter = new StringWriter();
        OperationResponse operationResponse;

        for (org.xml.sax.SAXParseException error : errors) {
            logger.debug("XML validation error on request: " + error.getMessage());
        }

        if (messageContext.getResponse() instanceof SoapMessage) {
            SaajSoapMessage soapMessage = (SaajSoapMessage)messageContext.getResponse();
            SoapBody body = soapMessage.getSoapBody();

            try {
                jaxbContext = JAXBContext.newInstance(OperationResponse.class);
                operationResponse = new OperationResponse(false,
                        new Error(Arrays.stream(errors)
                                .map(SAXException::getMessage)
                                .collect(Collectors.toList())));

                jaxbContext.createMarshaller().marshal(operationResponse, stringWriter);
            } catch (Exception e) {
                logger.error("Error marshalling error response: " + e.getMessage());
            }

            Source source = new StreamSource(new StringReader(stringWriter.toString()));
            Transformer identityTransform = TransformerFactory.newInstance().newTransformer();
            identityTransform.transform(source, body.getPayloadResult());

            try {
                stringWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }
}
