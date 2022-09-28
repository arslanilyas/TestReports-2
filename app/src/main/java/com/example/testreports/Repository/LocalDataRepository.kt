package com.example.testreports.Repository


import android.R.attr
import android.content.Context
import com.example.testreports.data.ContactItem
import kotlinx.coroutines.Dispatchers
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.ArrayList
import android.content.res.AssetManager
import com.example.testreports.utils.Constants
import org.w3c.dom.*
import java.io.*
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import android.R.attr.path
import android.os.Environment
import com.example.testreports.utils.XmlParserUtil
import java.lang.Exception


class LocalDataRepository() {

    var dispatcher = Dispatchers.IO


    suspend fun getAllRecords(context: Context): List<ContactItem> {

        XmlParserUtil.copyFile(context);

        val contactList: ArrayList<ContactItem> = ArrayList()
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
                val name = XmlParserUtil.getNodeValue(Constants.ContactName, elm)
                var companyName = XmlParserUtil.getNodeValue(Constants.CompanyName, elm)
                var contactTitle = XmlParserUtil.getNodeValue(Constants.ContactTitle, elm)
                var contactAddress = XmlParserUtil.getNodeValue(Constants.Address, elm)
                var contactCity = XmlParserUtil.getNodeValue(Constants.City, elm)
                var contactEmail = XmlParserUtil.getNodeValue(Constants.Email, elm)
                var contactRegion = XmlParserUtil.getNodeValue(Constants.Region, elm)
                var contactPostalCode = XmlParserUtil.getNodeValue(Constants.PostalCode, elm)
                var contactCountry = XmlParserUtil.getNodeValue(Constants.Country, elm)
                var contactPhone = XmlParserUtil.getNodeValue(Constants.Phone, elm)
                var customerID = XmlParserUtil.getNodeValue(Constants.CustomerID, elm)
                var contactFax = XmlParserUtil.getNodeValue(Constants.Fax, elm)

                var recordItem = ContactItem(
                    customerID,
                    companyName,
                    name,
                    contactTitle,
                    contactAddress,
                    contactCity,
                    contactEmail,
                    contactRegion,
                    contactPostalCode,
                    contactCountry,
                    contactPhone,
                    contactFax)



                contactList.add(recordItem)
            }
        }


        return contactList

    }



    suspend fun addContact(context: Context, contactItem: ContactItem): List<ContactItem> {

        XmlParserUtil.addNode(context, contactItem)
        return getAllRecords(context);
    }


    suspend fun updateContact(context: Context, contactItem: ContactItem): List<ContactItem> {

        XmlParserUtil.updateNode(context, contactItem)
        return getAllRecords(context);
    }


    suspend fun deleteContact(context: Context, contactID: String): List<ContactItem> {

        XmlParserUtil.deleteContact(context, contactID)
        return getAllRecords(context);
    }













}