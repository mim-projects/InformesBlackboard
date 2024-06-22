const OnLoadData = []
let globalLoader = null;

const createGlobalLoader = () => {
    globalLoader = document.createElement("div")
    globalLoader.classList.add("global-loader")
    const icon = document.createElement("i")
    icon.classList.add("pi", "pi-spinner")
    globalLoader.appendChild(icon)
    const span = document.createElement("span")
    globalLoader.appendChild(span)
    span.textContent = "#{bundle.loading}"
    document.getElementsByTagName("body")[0].appendChild(globalLoader)
}

const removeGlobalLoader = () => {
    if (globalLoader == null) return;
    document.getElementsByTagName("body")[0].removeChild(globalLoader)
    globalLoader = null
}

const GetGlobalCssRule = (name) => {
    return getComputedStyle(document.body).getPropertyValue(name)
}

const SetGlobalCssRule = (name, value) => {
    return document.body.style.setProperty(name, value)
}

const showNavigationDrawer = () => {
    let nav_drawer = document.querySelector(".navigator-drawer")
    if (nav_drawer) {
        let interval = 20
        let duration = 100
        let counter = 10
        nav_drawer.style.display = "flex"
        let animation = setInterval(() => {
            nav_drawer.style.opacity = counter / interval
            if (counter >= interval) clearInterval(animation)
            counter++
        }, duration / interval)
    }

}

const closeNavigationDrawer = () => {
    let drawer = document.querySelector(".navigator-drawer")
    drawer.style.opacity = "0"
    setTimeout(() => drawer.style.display = "none", 400)
}

const navigation = async (route, params = null) => {
    return await navigateRC([
        { name: "route", value: route },
        { name: "params", value: params }
    ])
}

const CreateCustomBadge = (id) => {
    const clazz = ["successful", "error", "firing", "read", "delivered", "resolved"];
    return clazz[id % clazz.length];
}

const ColorizeItemList = (list) => {
    list.childNodes.forEach(item => {
        if (!item.classList.contains("ui-selectcheckboxmenu-emptylabel")) {
            item.classList.add("custom-badge")
            item.classList.add("custom-badge-" + CreateCustomBadge(item.getAttribute("data-item-value")))
        }
    });
}

OnLoadData.push(() => {
    window.onclick = e => {
        // Ocultar navigation drawer al presionar sobre la parte sombreada
        if (e.target.classList.contains("navigator-drawer")) {
            let drawer_content = document.querySelector(".navigator-drawer .navigator-content")
            if (drawer_content === undefined) return null
            if (e.x + 20 > drawer_content.offsetWidth) closeNavigationDrawer()
        }
    }
})

window.onload = () => {
    OnLoadData.forEach(item => item())
}