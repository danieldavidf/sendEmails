import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static String removeSpaces(String str) {
		String strWithoutSpaces = str.replaceAll(" ","");
		return strWithoutSpaces;
	}
	
    public static void main(String[] args) throws IOException {

    	String csvPath =	"/home/your_path/senders.csv";
        String pathLocal =	"/home/your_path/sendCertificates/files/";
    	
        String subject = "ER 2019 - Certificate of Presentation (Tutorial)";

        SendMail sendMail = new SendMail();

        int cont = 0;

        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            String autor = data[0].trim();
            String emailTo = data[1].trim();
            String fileName = data[2].trim();

            String filePath = pathLocal+fileName;
            
            String msg =
                    "Dear, "+autor+",<br><br>" + 
                    "Please find attached your Certificate.<br><br>" + 
                    "Best regards,<br><br>" + 
                    "ER 2019 organization";

            if(new File(filePath).exists()) {
                cont++;
                sendMail.send(subject, msg, emailTo, filePath, fileName);
                System.out.println("to: " + emailTo + " - " + fileName + " - " + filePath);
            }else{
                System.out.println("IT DOESN'T EXIST. -> " + filePath);
            }
        }
        System.out.println(cont + " emails sent.");
        csvReader.close();

    }

}