const WaitExecuteReadFile = (progress = 0, onload = false) => {
    const container = document.createElement("div")
    container.textContent = progress + "%"
    if (onload === false) {
        container.className = "loader"
        document.getElementById("form_upload").appendChild(container)
        document.querySelector("#form_upload .ui-dialog-titlebar").style.display = "none"
        document.querySelector("#form_upload .ui-dialog-content").style.display = "none"
    } else {
        container.className = "loader-start"
        document.querySelector("body").appendChild(container)
    }

    let interval = null
    const OnCompleteListener = () => {
        OnExecuteComplete().then(async response => {
            try {
                console.log("Respuesta recibida de OnExecuteComplete.");

                const responseData = response.jqXHR.pfArgs.data;

                // Verifica si el campo existe y es una cadena JSON válida
                if (responseData === undefined || responseData === null) {
                    console.warn("No se encontraron datos en la respuesta. Finalizando proceso.");
                    clearInterval(interval);
                    return;
                }

                const data = JSON.parse(responseData);
                console.log("Datos JSON recibidos:", data);
                // Actualiza la vista con el progreso
                container.textContent = data.progress + "%";
                console.log("Progreso actualizado en pantalla:", data.progress + "%");

                // Verifica si el proceso está completo o falló
                if (("" + data.complete) === "true" || ("" + data.complete) === "null") {
                    if (onload === false) {
                        console.log("Eliminando contenedor de progreso.");
                        document.getElementById("form_upload").removeChild(container);
                    } else {
                        console.log("Eliminando contenedor de carga desde el cuerpo del documento.");
                        document.querySelector("body").removeChild(container);
                    }
                    clearInterval(interval);
                    PF("upload_wv").hide();

                    // Mensajes según el resultado
                    if (("" + data.complete) === "true") {
                        console.log("Carga completa. Mostrando mensaje de éxito.");
                        showFacesMessage([
                            {name: "severity", value: "info"},
                            {name: "title", value: "Successful"},
                            {name: "details", value: "Uploaded files and updated data"}
                        ]);
                    } else if (("" + data.complete) === "null") {
                        console.error("Carga fallida. Formato inválido o error de guardado.");

                        showFacesMessage([
                            {name: "severity", value: "error"},
                            {name: "title", value: "Error"},
                            {name: "details", value: "Invalid Format or Failed Save"}
                        ]);
                    }
                }
            } catch (e) {
                clearInterval(interval);
                console.error("Error during JSON parsing or processing:", e);
            }
        });
    };
    interval = setInterval(OnCompleteListener, 1000);

}

OnExecuteComplete().then(async response => {
    const data = JSON.parse(response.jqXHR.pfArgs.data)
    if (data.complete === false)
        WaitExecuteReadFile(data.progress, true)
})