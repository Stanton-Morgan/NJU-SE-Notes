let svgText = '201250048 沈霁昀'
let fontSize = 18
let svgWidth = fontSize * svgText.length
let svgHeight = svgWidth
let rotate = -45
watermarksvg = `url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='${svgWidth}px' height='${svgHeight}px'%3E  %3Ctext  x='${fontSize}' y='0'  fill-opacity='0.8' fill='red' transform='translate(0,${svgHeight})rotate(${rotate})'    font-size='${fontSize}'%3E${svgText}%3C/text%3E%3C/svg%3E")`
document.querySelector('.watermark').style.setProperty('--watermark', watermarksvg)