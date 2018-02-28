
var vertexShaderText =
  [
  'precision mediump float;',
  '',
  'attribute vec3 vertPosition;',
  // For colored sides
  //'attribute vec3 vertColor;',
  //'varying vec3 fragColor;',
  'attribute vec2 vertTexCoord;',
  'varying vec2 fragTexCoord;',
  'uniform mat4 mWorld;',
  'uniform mat4 mView;',
  'uniform mat4 mProj;',
  '',
  'void main()',
  '{',
  // For colored sides
  //' fragColor = vertColor;',
  ' fragTexCoord = vertTexCoord;',
  ' gl_Position = mProj * mView * mWorld * vec4(vertPosition, 1.0);',
  '}'
  ].join('\n');

var fragmentShaderText =
  [
  'precision mediump float;',
  '',
  // for colored sides
  //'varying vec3 fragColor;',
  'varying vec2 fragTexCoord;',
  'uniform sampler2D sampler;',
  '',
  'void main()',
  '{',
  // for colored sides
  //' gl_FragColor = vec4(fragColor, 1.0);',
  ' gl_FragColor = texture2D(sampler, fragTexCoord);',
  '}'
  ].join('\n');


var InitWebGL = function(){
  console.log('Its working!');

  var canvas = document.getElementById("gl");
  var gl = canvas.getContext("webgl")
  || canvas.getContext("experimental-webgl")
  || canvas.getContext("moz-webgl")
  || canvas.getContext("webkit-3d");
  if(gl){
  var extensions = gl.getSupportedExtensions();

  console.log(gl);
  console.log(extensions);

  gl.viewportWidth = canvas.width;
  gl.viewportHeight = canvas.height;

  gl.clearColor(0.2, 0.2, 0.2, 1.0);
  gl.clear(gl.COLOR_BUFFER_BIT);
  gl.enable(gl.DEPTH_TEST);
	gl.enable(gl.CULL_FACE);
	gl.frontFace(gl.CCW);
	gl.cullFace(gl.BACK);

  }
  else{
  console.log("Your browser doesn't support OpenGL.");
  }

  var vertexShader = gl.createShader(gl.VERTEX_SHADER);
  var fragmentShader = gl.createShader(gl.FRAGMENT_SHADER);

  gl.shaderSource(vertexShader, vertexShaderText);
  gl.shaderSource(fragmentShader, fragmentShaderText);

  gl.compileShader(vertexShader);
  if(!gl.getShaderParameter(vertexShader, gl.COMPILE_STATUS)){
    console.error('ERROR compiling vertex shader!', gl.getShaderInfoLog());
    return;
  }

  gl.compileShader(fragmentShader);
  if(!gl.getShaderParameter(fragmentShader, gl.COMPILE_STATUS)){
    console.error('ERROR compiling fragment shader!', gl.getShaderInfoLog());
    return;
  }

  var program = gl.createProgram();
  gl.attachShader(program, vertexShader);
  gl.attachShader(program, fragmentShader);
  gl.linkProgram(program);
  if(!gl.getProgramParameter(program, gl.LINK_STATUS)){
    console.error('ERROR linking program!', gl.getProgramInfoLog());
    return;
  }
  gl.validateProgram(program);
  if(!gl.getProgramParameter(program, gl.VALIDATE_STATUS)){
    console.error('ERROR validating program!', gl.getProgramInfoLog(program));
    return;
  }

  // Create buffer
/* //CREATE TRIANGLE
  var triangleVertices =
  [ // X, Y, Z        R, G, B
    0.0, 0.5, 0.0,    1.0, 0.0, 0.0,
    -0.5, -0.5, 0.0,  0.0, 1.0, 0.0,
    0.5, -0.5, 0.0,   0.0, 0.0, 1.0
  ];

  var triangleVertexBufferObject = gl.createBuffer();
  gl.bindBuffer(gl.ARRAY_BUFFER, triangleVertexBufferObject);
  gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(triangleVertices), gl.STATIC_DRAW);
*/
// CREATE CUBE
  var boxVertices =
    [ // X, Y, Z           U, V
      // Top
      -1.0, 1.0, -1.0,   0, 0,
      -1.0, 1.0, 1.0,    0, 1,
      1.0, 1.0, 1.0,     1, 1,
      1.0, 1.0, -1.0,    1, 0,

      // Left
      -1.0, 1.0, 1.0,    0, 0,
      -1.0, -1.0, 1.0,   1, 0,
      -1.0, -1.0, -1.0,  1, 1,
      -1.0, 1.0, -1.0,   0, 1,

      // Right
      1.0, 1.0, 1.0,    1, 1,
      1.0, -1.0, 1.0,   0, 1,
      1.0, -1.0, -1.0,  0, 0,
      1.0, 1.0, -1.0,   1, 0,

      // Front
      1.0, 1.0, 1.0,    1, 1,
      1.0, -1.0, 1.0,    1, 0,
      -1.0, -1.0, 1.0,    0, 0,
      -1.0, 1.0, 1.0,    0, 1,

      // Back
      1.0, 1.0, -1.0,    0, 0,
      1.0, -1.0, -1.0,    0, 1,
      -1.0, -1.0, -1.0,    1, 1,
      -1.0, 1.0, -1.0,    1, 0,

      // Bottom
      -1.0, -1.0, -1.0,   1, 1,
      -1.0, -1.0, 1.0,    1, 0,
      1.0, -1.0, 1.0,     0, 0,
      1.0, -1.0, -1.0,    0, 1,
    ];
	var boxIndices =
  	[
  		// Top
  		0, 1, 2,
  		0, 2, 3,

  		// Left
  		5, 4, 6,
  		6, 4, 7,

  		// Right
  		8, 9, 10,
  		8, 10, 11,

  		// Front
  		13, 12, 14,
  		15, 14, 12,

  		// Back
  		16, 17, 18,
  		16, 18, 19,

  		// Bottom
  		21, 20, 22,
  		22, 20, 23
  	];
  var boxVertexBufferObject = gl.createBuffer();
  gl.bindBuffer(gl.ARRAY_BUFFER, boxVertexBufferObject);
  gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(boxVertices), gl.STATIC_DRAW);
  var boxIndexBufferObject = gl.createBuffer();
	gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, boxIndexBufferObject);
	gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(boxIndices), gl.STATIC_DRAW);
  var positionAttribLocation = gl.getAttribLocation(program, 'vertPosition');
  var texCoordAttribLocation = gl.getAttribLocation(program, 'vertTexCoord');
  gl.vertexAttribPointer(
    positionAttribLocation, // attribute Location
    3, // number of elements per attribute
    gl.FLOAT, // type of elements
    gl.FALSE,
    5 * Float32Array.BYTES_PER_ELEMENT, // size of an individual vertex
    0 // offset from the beginning of a single vertex to this attribute
  );
  gl.vertexAttribPointer(
    texCoordAttribLocation, // attribute Location
    2, // number of elements per attribute
    gl.FLOAT, // type of elements
    gl.FALSE,
    5 * Float32Array.BYTES_PER_ELEMENT, // size of an individual vertex
    3 * Float32Array.BYTES_PER_ELEMENT // offset from the beginning of a single vertex to this attribute
  );
  gl.enableVertexAttribArray(positionAttribLocation);
  gl.enableVertexAttribArray(texCoordAttribLocation);

// CREATE CRATE TEXTURE
  var boxTexture = gl.createTexture();
  gl.bindTexture(gl.TEXTURE_2D, boxTexture);
  gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE);
  gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE);
  gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR);
  gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
  gl.texImage2D(
    gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA,
    gl.UNSIGNED_BYTE,
    document.getElementById('crate-image')
  );
  gl.bindTexture(gl.TEXTURE_2D, null);

  gl.useProgram(program); // tell OpenGL state machine which program should be active

  var matWorldUniformLocation = gl.getUniformLocation(program, 'mWorld');
  var matViewUniformLocation = gl.getUniformLocation(program, 'mView');
  var matProjUniformLocation = gl.getUniformLocation(program, 'mProj');

  var worldMatrix = new Float32Array(16);
  var viewMatrix = new Float32Array(16);
  var projMatrix = new Float32Array(16);
  mat4.identity(worldMatrix);
  mat4.lookAt(viewMatrix, [0, 0, -8], [0, 0, 0], [0, 1, 0]);
  mat4.perspective(projMatrix, glMatrix.toRadian(45), canvas.width/canvas.height, 0.1, 1000,0);

  gl.uniformMatrix4fv(matWorldUniformLocation, gl.FALSE, worldMatrix);
  gl.uniformMatrix4fv(matViewUniformLocation, gl.FALSE, viewMatrix);
  gl.uniformMatrix4fv(matProjUniformLocation, gl.FALSE, projMatrix);

  // Initialize global variable loop, which will be redefined depending on the button that is pressed
  var loop;

  document.getElementById('crate').onclick = function(){
    var identityMatrix = new Float32Array(16);
    mat4.identity(identityMatrix);
    var angle = 0;
    loop = function(){
      angle = performance.now() / 1000 / 6 * 2 * Math.PI;
      mat4.rotate(worldMatrix, identityMatrix, angle, [.2, .5, 0]);
      gl.uniformMatrix4fv(matWorldUniformLocation, gl.FALSE, worldMatrix);

      gl.clearColor(0.2, 0.2, 0.2, 1.0);
      gl.clear(gl.COLOR_BUFFER_BIT);

      gl.bindTexture(gl.TEXTURE_2D, boxTexture);
      gl.activeTexture(gl.TEXTURE0);

      gl.drawElements(gl.TRIANGLES, boxIndices.length, gl.UNSIGNED_SHORT, 0);

      requestAnimationFrame(loop);
    };
  }

  document.getElementById('world').onclick = function(){
    var identityMatrix = new Float32Array(16);
    mat4.identity(identityMatrix);
    var angle = 0;
    loop = function(){
      angle = performance.now() / 1000 / 6 * 2 * Math.PI;
      mat4.rotate(worldMatrix, identityMatrix, angle, [.2, .5, 0]);
      gl.uniformMatrix4fv(matWorldUniformLocation, gl.FALSE, worldMatrix);

      gl.clearColor(0.2, 0.2, 0.2, 1.0);
      gl.clear(gl.COLOR_BUFFER_BIT);



      requestAnimationFrame(loop);
    };
  }

  document.getElementById('reset').onclick = function(){
    var identityMatrix = new Float32Array(16);
    mat4.identity(identityMatrix);
    var angle = 0;
    loop = function(){
      angle = performance.now() / 1000 / 6 * 2 * Math.PI;
      mat4.rotate(worldMatrix, identityMatrix, angle, [.2, .5, 0]);
      gl.uniformMatrix4fv(matWorldUniformLocation, gl.FALSE, worldMatrix);

      gl.clearColor(0.2, 0.2, 0.2, 1.0);
      gl.clear(gl.COLOR_BUFFER_BIT);

      requestAnimationFrame(loop);
    };
  }

  // main render loop
  var identityMatrix = new Float32Array(16);
  mat4.identity(identityMatrix);
  var angle = 0;
  loop = function(){
    angle = performance.now() / 1000 / 6 * 2 * Math.PI;
    mat4.rotate(worldMatrix, identityMatrix, angle, [.2, .5, 0]);
    gl.uniformMatrix4fv(matWorldUniformLocation, gl.FALSE, worldMatrix);

    gl.clearColor(0.2, 0.2, 0.2, 1.0);
    gl.clear(gl.COLOR_BUFFER_BIT);

    requestAnimationFrame(loop);
  };

  requestAnimationFrame(loop);

};
