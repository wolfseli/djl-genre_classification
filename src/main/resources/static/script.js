function checkFiles(files) {
    console.log(files);

    if (files.length != 1) {
        alert("Bitte genau eine Datei hochladen.")
        return;
    }

    const fileSize = files[0].size / 1024 / 1024; // in MiB
    if (fileSize > 10) {
        alert("Datei zu gross (max. 10Mb)");
        return;
    }

    answerPart.style.visibility = "visible";
    const file = files[0];

    // Preview
    if (file) {
        preview.src = URL.createObjectURL(files[0])
    }

    // Upload
    const formData = new FormData();
    for (const name in files) {
        formData.append("image", files[name]);
    }

    fetch('/analyze', {
        method: 'POST',
        headers: {
        },
        body: formData
    }).then(
        response => {

            console.log(response)
            response.text().then(function (text) {
                // parse the JSON String into a JavaScript Object
                const json = JSON.parse(text);
                
                // clear the answer div
                answer.innerHTML = "";

                // iterate over the object and append the key-value pairs to the answer div.
                // The object looks like this:  [ { "className": "Metal", "probability": 0.862569272518158 }, { "className": "Rock und Pop", "probability": 0.08717569708824158 }, { "className": "Country", "probability": 0.040648773312568665 }, { "className": "Reggae", "probability": 0.006215967237949371 }, { "className": "HipHop", "probability": 0.0024358194787055254 } ]
                // Also show the probability as a bar
                for (const entry of json) {
                    const div = document.createElement("div");
                    div.innerHTML = entry.className + ": " + (entry.probability * 100).toFixed(2) + "%";
                    div.style.width = entry.probability * 100 + "%";
                    div.style.backgroundColor = "lightcoral";
                    div.style.margin = "10px 0";
                    answer.appendChild(div);
                }
            });

        }
    ).then(
        success => console.log(success)
    ).catch(
        error => console.log(error)
    );

}