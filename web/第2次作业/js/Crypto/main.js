//MAIN
// Parameters optimized according to tests.
function writeMsgToCanvas(canvasid, msg, pass) {
    return writeMsgToCanvas_base(canvasid, msg, pass, false, 1);
}


//Read msg from the image in canvasid.
//Return msg (null -> fail)
function readMsgFromCanvas(canvasid, pass) {
    return readMsgFromCanvas_base(canvasid, pass, false, 1)[1];
}


//load image from html5 input and execute callback() if successful
function loadIMGtoCanvas(inputid, canvasid, callback, ) {
    var input = document.getElementById(inputid);
    if (input.files && input.files[0]) {
        var f = input.files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            var image = new Image();
            image.onload = function () {
                var w = image.width;
                var h = image.height;

                var canvas = document.createElement('canvas');
                canvas.id = canvasid;
                canvas.width = w;
                canvas.height = h;
                canvas.style.display = "none";
                var body = document.getElementsByTagName("body")[0];
                body.appendChild(canvas);
                var context = canvas.getContext('2d');
                context.drawImage(image, 0, 0, image.width, image.height, 0, 0, w, h);
                callback();
                document.body.removeChild(canvas);
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        alert('NO IMG FILE SELECTED');
        return 'ERROR PROCESSING IMAGE!';
    }
}
function loadIMGtoCanvas1(inputid, canvasid, callback, maxsize, imgPath) {
    maxsize=(maxsize=== undefined)?0:maxsize;
    console.log(456);
    var image = new Image();
    image.onload = function() {
        var w=image.width;
        var h=image.height;
        if(maxsize>0){
            if(w>maxsize){
                h=h*(maxsize/w);
                w=maxsize;
            }
            if(h>maxsize){
                w=w*(maxsize/h);
                h=maxsize;
            }
            w=Math.floor(w);
            h=Math.floor(h);
        }
        var canvas = document.createElement('canvas');
        canvas.id = canvasid;
        canvas.width = w;
        canvas.height = h;
        canvas.style.display = "none";
        var body = document.getElementsByTagName("body")[0];
        body.appendChild(canvas);
        var context = canvas.getContext('2d');
        context.drawImage(image, 0, 0,image.width,image.height,0,0,w,h);
        callback();
        document.body.removeChild(canvas);
    };
    image.src =  imgPath;
    console.log(image.src);

}