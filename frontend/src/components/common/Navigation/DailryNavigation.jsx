import { useState, useEffect } from 'react';
import * as S from './Navigation.styled';
import NameArea from './NameArea';
import Text from '../Text/Text';
import NavigationItem from '../NavigationItem/NavigationItem';
import NavigationInput from '../NavigationItem/NavigationInput';
import { NavigationItemIcon } from '../../../assets/svg';
import {
  getDailry,
  getPages,
  postDailry,
  postPage,
} from '../../../apis/dailryApi';
import { useDailryContext } from '../../../hooks/useDailryContext';
import DailryHamburger from '../Hamburger/DailryHamburger';

const DailryNavigation = () => {
  const [dailryItems, setDailryItems] = useState([]);
  const [editingDailry, setEditingDailry] = useState(null);
  const { currentDailry, setCurrentDailry } = useDailryContext();

  useEffect(() => {
    (async () => {
      const response = await getDailry();
      const updatedDailryItems = response.data;
      setDailryItems(updatedDailryItems);
      if (
        !updatedDailryItems
          .map(({ dailryId }) => dailryId)
          .find((id) => id === currentDailry.dailryId)
      ) {
        setCurrentDailry({
          ...currentDailry,
          dailryId: updatedDailryItems[0].dailryId,
        });
      }
    })();
  }, [editingDailry]);

  const handleItemClick = async (dailryId) => {
    const response = await getPages(dailryId);
    const pageIds = response.data.pages.map(({ pageId }) => pageId);
    setCurrentDailry({ dailryId, pageNumber: 1, pageIds });
  };

  const isCurrent = (id) => {
    return currentDailry.dailryId === id;
  };

  const handleAddClick = async () => {
    const response = await postDailry({ title: '새 다일리' });
    const { dailryId } = await response.data;
    setEditingDailry(dailryId);
    await postPage(dailryId);
  };

  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      <Text css={S.ItemName}>My Dailry</Text>
      {dailryItems.map(({ dailryId, title }) => {
        if (editingDailry === dailryId) {
          return (
            <NavigationInput
              key={dailryId}
              dailryId={dailryId}
              title={title}
              setEditingDailry={setEditingDailry}
            />
          );
        }
        return (
          <NavigationItem
            key={dailryId}
            onClick={() => handleItemClick(dailryId)}
            current={isCurrent(dailryId)}
            icon={<NavigationItemIcon />}
            hamburger={
              <DailryHamburger
                setEditingDailry={setEditingDailry}
                dailryId={dailryId}
              />
            }
          >
            {title}
          </NavigationItem>
        );
      })}
      <S.AddDailry onClick={handleAddClick}>+ 새 다일리 만들기</S.AddDailry>
    </S.NavigationWrapper>
  );
};

export default DailryNavigation;
