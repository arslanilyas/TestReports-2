package com.example.testreports.utils

import android.content.Context
import com.example.testreports.data.ContactItem
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import android.util.Log
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer


class XmlParserUtil {

    companion object {


        public fun copyFile(context: Context) {
            val assetManager = context.assets
            val path = context.getFilesDir()

            val file = File(path, "ab.xml")
            if (file.exists()) {
                return
            }

            val `in` = assetManager.open("ab.xml")
            val out: OutputStream = FileOutputStream(File(path, "ab.xml"))
            val buffer = ByteArray(1024)
            var read = `in`.read(buffer)
            while (read != -1) {
                out.write(buffer, 0, read)
                read = `in`.read(buffer)
            }

        }

        public fun addNode(context: Context, contactItem: ContactItem) {


            val path = context.getFilesDir()
            val file = File(path, "ab.xml")

            val istream: InputStream = FileInputStream(file);

            val builderFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder: DocumentBuilder = builderFactory.newDocumentBuilder()
            val doc: Document = docBuilder.parse(istream)


            val root: Element = doc.getDocumentElement()
            val newContact: Element = doc.createElement(Constants.Contact)

            val id: Element = doc.createElement(Constants.CustomerID)
            id.appendChild(doc.createTextNode(contactItem.customerID))
            newContact.appendChild(id)

            val companyName: Element = doc.createElement(Constants.CompanyName)
            companyName.appendChild(doc.createTextNode(contactItem.companyName))
            newContact.appendChild(companyName)

            val name: Element = doc.createElement(Constants.ContactName)
            name.appendChild(doc.createTextNode(contactItem.contactName))
            newContact.appendChild(name)


            val contactTitle: Element = doc.createElement(Constants.ContactTitle)
            contactTitle.appendChild(doc.createTextNode(contactItem.contactTitle))
            newContact.appendChild(contactTitle)


            val contactAddress: Element = doc.createElement(Constants.Address)
            contactAddress.appendChild(doc.createTextNode(contactItem.contactAddress))
            newContact.appendChild(contactAddress)

            val contactCity: Element = doc.createElement(Constants.City)
            contactCity.appendChild(doc.createTextNode(contactItem.contactCity))
            newContact.appendChild(contactCity)


            val contactEmail: Element = doc.createElement(Constants.Email)
            contactEmail.appendChild(doc.createTextNode(contactItem.contactEmail))
            newContact.appendChild(contactEmail)

            val contactRegion: Element = doc.createElement(Constants.Region)
            contactRegion.appendChild(doc.createTextNode(contactItem.contactEmail))
            newContact.appendChild(contactRegion)

            val contactPostalCode: Element = doc.createElement(Constants.PostalCode)
            contactPostalCode.appendChild(doc.createTextNode(contactItem.contactPostalCode))
            newContact.appendChild(contactPostalCode)

            val contactCountry: Element = doc.createElement(Constants.Country)
            contactCountry.appendChild(doc.createTextNode(contactItem.contactCountry))
            newContact.appendChild(contactCountry)

            val phone: Element = doc.createElement(Constants.Phone)
            phone.appendChild(doc.createTextNode(contactItem.contactPhone))
            newContact.appendChild(phone)


            val contactFax: Element = doc.createElement(Constants.Fax)
            contactFax.appendChild(doc.createTextNode(contactItem.contactFax))
            newContact.appendChild(contactFax)



            root.appendChild(newContact)


            val transformerFactory = TransformerFactory.newInstance()
            val transformer = transformerFactory.newTransformer()
            val dSource = DOMSource(doc)
            val result = StreamResult(file)
            transformer.transform(dSource, result)


        }


        public fun deleteContact(context: Context, contactId: String) {


            val path = context.getFilesDir()
            val file = File(path, "ab.xml")

            val istream: InputStream = FileInputStream(file);

            val builderFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder: DocumentBuilder = builderFactory.newDocumentBuilder()
            val doc: Document = docBuilder.parse(istream)
            val nList: NodeList = doc.getElementsByTagName(Constants.Contact)




            for (i in 0 until nList.length) {
                if (nList.item(0).nodeType === Node.ELEMENT_NODE) {
                    val elm: Element = nList.item(i) as Element
                    if (checkNodeID(Constants.CustomerID, contactId, elm)) {
                        elm.parentNode.removeChild(elm)
                        break
                    }


                }
            }


            val transformerFactory = TransformerFactory.newInstance()
            val transformer = transformerFactory.newTransformer()
            val dSource = DOMSource(doc)
            val result = StreamResult(file)
            transformer.transform(dSource, result)


        }

        private fun checkNodeID(tag: String?, nodeId: String?, element: Element): Boolean {
            val nodeList = element.getElementsByTagName(tag)
            val node = nodeList.item(0)
            if (node != null) {
                if (node.hasChildNodes()) {
                    val child = node.firstChild
                    while (child != null) {
                        if (child.nodeType == Node.TEXT_NODE && nodeId == child.nodeValue) {
                            return true
                        } else {
                            return false
                        }
                    }
                }
            }
            // Returns nothing if nothing was found
            return false
        }


        public fun updateNode(context: Context,contactItem: ContactItem) {


            val path = context.getFilesDir()
            val file = File(path, "ab.xml")

            val istream: InputStream = FileInputStream(file);

            val builderFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder: DocumentBuilder = builderFactory.newDocumentBuilder()
            val doc: Document = docBuilder.parse(istream)
            val nList: NodeList = doc.getElementsByTagName(Constants.Contact)


            // Iterating through this list
            for (i in 0 until nList.length) {
                if (nList.item(0).nodeType === Node.ELEMENT_NODE) {
                    val elm: Element = nList.item(i) as Element
                    var contactId = getNodeValue(Constants.CustomerID, elm);
                    if(contactId == contactItem.customerID) {
                        updateNodeValue(Constants.ContactName, contactItem.contactName, elm);
                        updateNodeValue(Constants.Phone, contactItem.contactPhone, elm);
                        updateNodeValue(Constants.CompanyName, contactItem.companyName, elm);
                        updateNodeValue(Constants.ContactTitle, contactItem.contactTitle, elm);
                        updateNodeValue(Constants.Address, contactItem.contactAddress, elm);
                        updateNodeValue(Constants.City, contactItem.contactCity, elm);
                        updateNodeValue(Constants.Email, contactItem.contactEmail, elm);
                        updateNodeValue(Constants.Region, contactItem.contactRegion, elm);
                        updateNodeValue(Constants.PostalCode, contactItem.contactPostalCode, elm);
                        updateNodeValue(Constants.Country, contactItem.contactCountry, elm);
                        updateNodeValue(Constants.Fax, contactItem.contactFax, elm);
                    }

                }
            }


            val transformerFactory = TransformerFactory.newInstance()
            val transformer = transformerFactory.newTransformer()
            val dSource = DOMSource(doc)
            val result = StreamResult(
                file
            ) // To save it in the Internal Storage

            transformer.transform(dSource, result)


        }

        private fun updateNodeValue(tag: String?, updateVal: String?, element: Element): String? {
            val nodeList = element.getElementsByTagName(tag)
            val node = nodeList.item(0)
            if (node != null) {
                if (node.hasChildNodes()) {
                    val child = node.firstChild
                    while (child != null) {
                        if (child.nodeType == Node.TEXT_NODE) {
                            child.nodeValue = updateVal;
                            return child.nodeValue
                        }
                    }
                }
            }
            // Returns nothing if nothing was found
            return ""
        }


        // A function to get the node value while parsing
        public fun getNodeValue(tag: String?, element: Element): String? {
            val nodeList = element.getElementsByTagName(tag)
            val node = nodeList.item(0)
            if (node != null) {
                if (node.hasChildNodes()) {
                    val child = node.firstChild
                    while (child != null) {
                        if (child.nodeType == Node.TEXT_NODE) {
                            return child.nodeValue
                        }
                    }
                }
            }
            // Returns nothing if nothing was found
            return ""
        }



        fun converXmlToJson(context: Context):String{

            val path = context.getFilesDir()
            val file = File(path, "ab.xml")
            val istream: InputStream = FileInputStream(file);
            val builderFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder: DocumentBuilder = builderFactory.newDocumentBuilder()
            val doc: Document = docBuilder.parse(istream)

            val tf = TransformerFactory.newInstance()
            val transformer: Transformer = tf.newTransformer()
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
            val writer = StringWriter()
            transformer.transform(DOMSource(doc), StreamResult(writer))
            val output: String = writer.buffer.toString().replace("\n|\r|\t", "")


            val xmlToJson: XmlToJson =
                XmlToJson.Builder(output).forceIntegerForPath("/AddressBook/Contact/CustomerID")
                    .build()

            Log.d("XmlToJSON",xmlToJson.toString())
            return xmlToJson.toString()
        }
    }

}