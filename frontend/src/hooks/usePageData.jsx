const usePageData = () => {
  const formData = new FormData();

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

  const appendPageDataToFormData = (
    thumbnail,
    updatedDecorateComponents,
    deletedElementIds,
  ) => {
    formData.append('thumbnail', thumbnail);
    formData.append(
      'dailryPageRequest',
      convertDecorateComponentsToBlob(
        updatedDecorateComponents,
        deletedElementIds,
      ),
    );
  };

  return { appendPageDataToFormData, formData };
};

export default usePageData;
