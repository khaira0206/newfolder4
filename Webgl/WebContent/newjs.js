<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>Rotating Pendulum (with Buttons)</title>
  </head>
   <body >

<script type="text/javascript"></script>
<canvas id="glscreen"></canvas>
<script type="text/javascript">// <![CDATA[
var gl;
  var canvas;

  window.onload = init;

var buffer;
  function init() {

	canvas        = document.getElementById('glscreen');
	gl            = canvas.getContext('experimental-webgl');
	canvas.width  = 640;
	canvas.height = 480;

	gl.viewport(0, 0, gl.drawingBufferWidth, gl.drawingBufferHeight);
	 buffer = gl.createBuffer();
  gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
  gl.bufferData(
    gl.ARRAY_BUFFER, 
    new Float32Array([
      -1.0, -1.0, 
       1.0, -1.0, 
      -1.0,  1.0, 
      -1.0,  1.0, 
       1.0, -1.0, 
       1.0,  1.0]), 
    gl.STATIC_DRAW
  );

	render();

  }

  function render() {

    window.requestAnimationFrame(render, canvas);

    gl.clearColor(1.0, 0.0, 0.0, 1.0);
    gl.clear(gl.COLOR_BUFFER_BIT);

  }
</script>
 </body>
</html>
