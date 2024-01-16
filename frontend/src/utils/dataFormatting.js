export const convertDataToCanvasImageData = (data) => {
  const uintArray = new Uint8ClampedArray(JSON.parse(JSON.parse(data).data));
  return new ImageData(uintArray, 300, 150);
};
