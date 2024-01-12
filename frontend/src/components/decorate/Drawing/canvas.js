export const createCanvasElement = (id) => {
  const canvas = document.getElementById(id);
  const ctx = canvas.getContext('2d');
  ctx.fillStyle = 'white';
  ctx.fillRect(0, 0, canvas.width, canvas.height);

  return { canvas, ctx };
};

export const canvasToImage = (id) => {
  const canvas = document.getElementById(id);
  const imgBase64 = canvas.toDataURL('image/jpeg', 'image/octet-stream');
  const decodeImg = atob(imgBase64.split(',')[1]);

  const array = [];
  for (let i = 0; i < decodeImg.length; i += 1) {
    array.push(decodeImg.charCodeAt(i));
  }

  const file = new Blob([new Uint8Array(array)], { type: 'image/jpeg' });
  const fileName = `canvas_img_${new Date().getMilliseconds()}.jpg`;
  const formData = new FormData();

  formData.append('file_give', file, fileName);

  return imgBase64;
};
