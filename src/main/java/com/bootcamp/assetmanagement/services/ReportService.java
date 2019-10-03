/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.CobaAssetManagement.services;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class ReportService {
    
    public String generateReportDipinjam() {
        try {
            Connection con = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsimrs", "root", "");

            } catch (Exception e) {
                e.printStackTrace();
            }

            String path = "D:\\MII\\Final\\CobaAssetManagement\\src\\main\\resources\\";
            // Compile the Jasper report from .jrxml to .japser
            JasperReport jasperReport = JasperCompileManager.compileReport(path + "reports\\reportasset.jrxml");

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("id", id);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
//            byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, con);
            
            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "static\\reportasset.pdf");
            System.out.println("Done");

            return "Report successfully generated";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error--> check the console log";
        }
    }
    
    public String generateReportReady() {
        try {
            Connection con = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsimrs", "root", "");

            } catch (Exception e) {
                e.printStackTrace();
            }

            String path = "D:\\MII\\Final\\CobaAssetManagement\\src\\main\\resources\\";
            // Compile the Jasper report from .jrxml to .japser
            JasperReport jasperReport = JasperCompileManager.compileReport(path + "reports\\reportready.jrxml");

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("id", id);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
//            byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, con);
            
            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "static\\reportready.pdf");
            System.out.println("Done");

            return "Report successfully generated";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error--> check the console log";
        }
    }
    
}
