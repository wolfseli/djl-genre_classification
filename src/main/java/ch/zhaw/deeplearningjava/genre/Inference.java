package ch.zhaw.deeplearningjava.genre;

import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Inference {

    Predictor<Image, Classifications> predictor;

    public Inference() {
        try {
            // Modell wird aus dem Verzeichnis models geladen
            Model model = Models.getModel();
            Path modelDir = Paths.get("models");
            model.load(modelDir, Models.MODEL_NAME);

            // Ein Translator wird erstellt, der die Bilddaten vorverarbeitet (Größenänderung und Umwandlung in ein Tensor-Format) 
            // und die Vorhersage in Wahrscheinlichkeiten umwandelt.
            Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
                    .addTransform(new Resize(Models.IMAGE_WIDTH, Models.IMAGE_HEIGHT))
                    .addTransform(new ToTensor())
                    .optApplySoftmax(true)
                    .build();
            predictor = model.newPredictor(translator);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Die Methode predict nimmt ein Bild als Byte-Array entgegen und gibt die Klassifizierung zurück.
    public Classifications predict(byte[] image) throws ModelException, TranslateException, IOException {
        InputStream is = new ByteArrayInputStream(image);
        BufferedImage bi = ImageIO.read(is);
        Image img = ImageFactory.getInstance().fromImage(bi);

        Classifications predictResult = this.predictor.predict(img);
        return predictResult;
    }
}
