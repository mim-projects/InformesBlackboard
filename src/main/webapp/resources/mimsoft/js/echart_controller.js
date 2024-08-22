const allCharts = []
const echartAppend = (_id, echart, callback) => {
    let find = false
    for (let chartIndex = 0; chartIndex < allCharts.length; chartIndex++) {
        if (allCharts[chartIndex].id === _id) {
            allCharts[chartIndex] = {id: _id, g: echart, c: callback}
            find = true
            break
        }
    }
    if (!find) allCharts.push({id: _id, g: echart, c: callback})
    echart.on("click", callback)
    return echart
}
window.addEventListener("resize", () => allCharts.forEach(items => items.g.resize()))