import { useState } from 'react';

const useDomContentsLoaded = () => {
  const [contentsLoaded, setContentsLoaded] = useState(false);
  document.addEventListener('DOMContentLoaded', () => {
    setContentsLoaded(true);
  });

  return contentsLoaded;
};

export default useDomContentsLoaded;
