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
            try { if (JSON.parse(response.jqXHR.pfArgs.data) === undefined) clearInterval(interval) }
            catch (e) { clearInterval(interval) }

            const data = JSON.parse(response.jqXHR.pfArgs.data)
            container.textContent = data.progress + "%"
            if (("" + data.complete) === "true" || ("" + data.complete) === "null") {
                if (onload === false) document.getElementById("form_upload").removeChild(container)
                else document.querySelector("body").removeChild(container)
                clearInterval(interval)
                PF("upload_wv").hide()
                if (("" + data.complete) === "true") showFacesMessage([
                    {name: "severity", value: "info"},
                    {name: "title", value: "Successful"},
                    {name: "details", value: "Uploaded files and updated data"}
                ]);
                else if (("" + data.complete) === "null") showFacesMessage([
                    {name: "severity", value: "error"},
                    {name: "title", value: "Error"},
                    {name: "details", value: "Invalid Format or Failed Save"}
                ])
            }
        })
    }
    interval = setInterval(OnCompleteListener, 1000)
}

OnExecuteComplete().then(async response => {
    const data = JSON.parse(response.jqXHR.pfArgs.data)
    if(data.complete === false) WaitExecuteReadFile(data.progress, true)
})