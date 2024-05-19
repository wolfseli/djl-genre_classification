// legt das Paket fest, zu dem die Klasse gehört
package ch.zhaw.deeplearningjava.genre;

// importieren der notwendigen Klassen aus dem Spring Framework
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// importieren, nimmt HTTP-Anfragen entgegen und antwortet darauf
@RestController
public class ClassificationController {

    // Instanz der Klasse Inference, die Logik zur Klassifizierung der hochgeladenen Bilder enthält
    private Inference inference = new Inference();

    //GET-Endpunkt /ping, gibt Bestätigungsnachricht zurück wenn der Service läuft
    @GetMapping("/ping")
    public String ping() {
        return "Classification app is up and running!";
    }

    //POST-Endpunkt /analyze, nimmt ein Bild entgegen und gibt die Klassifizierung zurück
    @PostMapping(path = "/analyze")
    public String predict(@RequestParam("image") MultipartFile image) throws Exception {
        System.out.println(image);
        return inference.predict(image.getBytes()).toJson();
    }

}

// Der Code definiert eine REST-API mit zwei Endpunkten. 
// Der GET-Endpunkt /ping prüft, ob der Dienst läuft.
//Der POST-Endpunkt /analyze nimmt ein Bild entgegen, klassifiziert es und gibt das Ergebnis als JSON zurück.