import { styled } from 'styled-components';
import { INPUT } from '../../styles/color';

export const CommunityWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;
  justify-content: start;
  align-items: center;

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
  width: 800px;
`;

export const SortWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 12px;
`;

export const PostWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 12px;

  width: 700px;
`;

export const HeadWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;

  width: 100%;
`;

export const RowFlex = styled.div`
  display: flex;
  flex-direction: row;
  gap: 8px;
`;

export const DailryWrapper = styled.img`
  width: 600px;
  aspect-ratio: 1.35/1;

  border: 1px solid #000000;
  border-radius: 8px;
`;

export const WriteContentArea = styled.input`
  width: 70%;
  height: 30px;
  margin-top: 20px;
  padding: 4px;

  border: 1px bold ${INPUT.default};
`;

export const TagWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;
  gap: 12px;

  width: 70%;
`;

export const WriteTagArea = styled.input`
  width: 120px;
  height: 30px;
  padding: 8px;

  border: 1px solid ${INPUT.default};}
`;

export const AddTagWrapper = styled.button`
  width: 20px;
  height: 20px;
  border: 1px solid #000000;
  border-radius: 10px;

  font-size: 16px;
  line-height: 20px;
`;
