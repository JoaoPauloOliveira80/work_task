package APPLICATION.utils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import javax.swing.JTable;
import java.io.FileOutputStream;
import java.io.IOException;

public class JTableToPDF {

    public static void saveTableToPDF(JTable table, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            int rowsPerPage = 30; // Define o número de linhas por página

            // Cabeçalho da tabela
            // ...

            // Dados da tabela
            // ...

            // Salvar o documento PDF
            document.save(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso:
        JTable myTable = new JTable(); // Substitua pelo seu JTable
        saveTableToPDF(myTable, "table_data.pdf");
    }
}
