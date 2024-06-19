import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useDailryContext } from './useDailryContext';
import { getPreviewPages } from '../apis/dailryApi';

const useSetCurrentDailryWithPath = () => {
  const { setCurrentDailry } = useDailryContext();
  const location = useLocation();
  const dailryIdMatch = location.pathname.match(/\/dailry\/(\d+)\/\d+/);
  const currentDailryId = dailryIdMatch ? Number(dailryIdMatch[1]) : null;

  useEffect(() => {
    (async () => {
      if (currentDailryId) {
        const result = await getPreviewPages(currentDailryId);

        setCurrentDailry({
          id: currentDailryId,
          pages: result.data.pages,
        });
      }
    })();
  }, [currentDailryId]);
};

export default useSetCurrentDailryWithPath;
