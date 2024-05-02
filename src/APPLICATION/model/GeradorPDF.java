package APPLICATION.model;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import APPLICATION.utils.Utils;

public class GeradorPDF {
    Utils utils = new Utils();
    
    public void gerarPDF(String titulo, List<Object> titulosColunas, List<List<Object>> dados) {
        Document document = new Document(PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(document, new FileOutputStream("hora110.pdf"));
            document.open();

            // Adicionar o título acima da primeira coluna
            PdfPCell titleCell = new PdfPCell(new Phrase("                       "+titulo));
            titleCell.setColspan(1); // Mesclar com apenas uma coluna
            titleCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); // Centralizar o título
            titleCell.setBorder(PdfPCell.NO_BORDER); // Remover a borda para que pareça parte da tabela
            PdfPTable titleTable = new PdfPTable(1);
            titleTable.setWidthPercentage(100);
            titleTable.addCell(titleCell);
            document.add(titleTable);

            // Criar uma tabela com o mesmo número de colunas que os dados da tabela
            PdfPTable table = new PdfPTable(titulosColunas.size());

            // Adicionar os títulos das colunas à tabela
            for (Object tituloColuna : titulosColunas) {
                PdfPCell cell = new PdfPCell(new Phrase(tituloColuna.toString()));
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); // Centralizar o título da coluna
                table.addCell(cell);
            }

            // Adicionar os dados da tabela ao PDF
            for (List<Object> linha : dados) {
                for (Object cell : linha) {
                    PdfPCell cellPdf = new PdfPCell(new Phrase(cell.toString()));
                    cellPdf.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); // Centralizar o conteúdo da célula
                    table.addCell(cellPdf);
                }
            }
            String msg  = "Pdf gerado com sucesso";
            utils.messageCrud(msg);

            document.add(table);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}