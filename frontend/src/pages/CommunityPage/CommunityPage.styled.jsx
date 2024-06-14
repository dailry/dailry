import { styled } from 'styled-components';
import { COMMUNITY } from '../../styles/color';
import { HeartIcon } from '../../assets/svg';

export const CommunityWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;
  justify-content: start;
  align-items: center;

  padding: 12px;
  width: 100%;
  height: 100%;
  overflow: auto;
`;

export const HeaderWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;

  padding: 20px;
  width: 80%;
  max-width: 800px;
`;

export const SortWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 12px;
`;

export const OrderByButton = styled.button`
  font-weight: ${({ current }) => (current ? 'bold' : 'normal')};
`;

export const LikeWrapper = styled.button`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 40px;
  font-size: 12px;
`;

export const PostWrapper = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 12px;

  padding: 12px;
  width: 70%;
  max-width: 700px;

  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 1px;
    background-color: ${COMMUNITY.line};
  }

  &:nth-last-child(-n + 2)::after {
    display: none;
  }
`;

export const HeadWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: start;

  width: 100%;
`;

export const RowFlex = styled.div`
  display: flex;
  flex-direction: row;
  align-items: start;
  gap: 8px;
`;

export const DailryWrapper = styled.img`
  margin-top: 12px;
  width: 80%;
  max-width: 600px;
  aspect-ratio: 1.35/1;

  border: 1px solid ${COMMUNITY.line};
  border-radius: 8px;
`;

export const ContentWrapper = styled.div`
  width: 100%;
`;

export const WriteContentArea = styled.input`
  width: 100%;
  height: 30px;
  margin-top: 20px;
  padding: 4px;

  border: 1px bold ${COMMUNITY.line};
`;

export const TagsWrapper = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: start;
  align-items: center;
  gap: 12px;

  width: 100%;
`;

export const TagWrapper = styled.div`
  display: inline-flex;
  flex-direction: row;
  justify-content: start;
  gap: 4px;

  padding: 0 8px;

  border: 1px solid ${COMMUNITY.line};
  border-radius: 12px;
`;

export const TagDeleteButton = styled.button`
  color: ${COMMUNITY.delete};
`;

export const WriteTagArea = styled.input`
  width: 120px;
  height: 30px;
  padding: 8px;

  border: 1px solid ${COMMUNITY.line};}
`;

export const LikeIcon = styled(HeartIcon)`
  width: 30px;
  height: 30px;
  fill: ${({ liked }) => (liked ? COMMUNITY.like : COMMUNITY.noLike)};
`;
