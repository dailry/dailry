import { useState } from 'react';

const usePageData = () => {
  const formData = new FormData();
  const [thumbnail, setThumbnail] = useState(null);

  const onUploadFile = (e) => {
    if (!e.target.files) {
      return;
    }

    setThumbnail(e.target.files[0]);
  };

  const convertDecorateComponentsToBlob = (
    updatedDecorateComponents,
    deletedElementIds,
  ) => {
    const blob = new Blob(
      [
        JSON.stringify({
          background: '무지 테스트',
          elements: updatedDecorateComponents,
          deletedElementIds,
        }),
      ],
      { type: 'application/json' },
    );

    return blob;
  };

  const getPageFormData = (updatedDecorateComponents, deletedElementIds) => {
    formData.append('thumbnail', thumbnail);
    formData.append(
      'dailryPageRequest',
      convertDecorateComponentsToBlob(
        updatedDecorateComponents,
        deletedElementIds,
      ),
    );

    return formData;
  };

  return { getPageFormData, onUploadFile };
};

export default usePageData;
