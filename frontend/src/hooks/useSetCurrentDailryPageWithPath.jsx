import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useDailryContext } from './useDailryContext';

const useSetCurrentDailryPageWithPath = () => {
  const location = useLocation();
  const { currentDailry, setCurrentDailryPage } = useDailryContext();
  const pageNumberMatch = location.pathname.match(/\/dailry\/\d+\/(\d+)/);
  const currentPageNumber = pageNumberMatch ? Number(pageNumberMatch[1]) : 1;

  const getCurrentDailryPage = (pageNumber) => {
    return currentDailry?.pages.find((page) => page.pageNumber === pageNumber);
  };

  useEffect(() => {
    if (currentDailry.id && currentPageNumber) {
      setCurrentDailryPage(getCurrentDailryPage(currentPageNumber));
    }
  }, [currentPageNumber]);

  useEffect(() => {
    setCurrentDailryPage(getCurrentDailryPage(1));
  }, [currentDailry.id]);
};

export default useSetCurrentDailryPageWithPath;
