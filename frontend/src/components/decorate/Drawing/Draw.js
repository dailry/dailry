class Draw {
  constructor(canvas, ctx) {
    this.canvas = canvas;
    this.ctx = ctx;
    this.canvas.width = canvas.getBoundingClientRect().width;
    this.canvas.height = canvas.getBoundingClientRect().height;
    this.coord = { x: 0, y: 0 };
  }

  reposition(event) {
    this.coord.x =
      event.clientX - this.canvas.getBoundingClientRect().left.toFixed();
    this.coord.y =
      event.clientY - this.canvas.getBoundingClientRect().top.toFixed();
  }

  move(event) {
    this.ctx.beginPath();
    this.ctx.moveTo(this.coord.x, this.coord.y);
    this.reposition(event);
    this.ctx.lineTo(this.coord.x, this.coord.y);
    this.ctx.stroke();
  }

  erase(event) {
    this.ctx.strokeStyle = 'white';
    this.move(event);
  }

  setColor(color) {
    this.ctx.strokeStyle = color;
  }

  setSize(size) {
    this.ctx.lineWidth = size;
  }

  getInfo() {
    const imgBase64 = this.canvas.toDataURL('image/jpeg', 'image/octet-stream');
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
  }
}

export default Draw;
