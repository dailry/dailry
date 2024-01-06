import styled from 'styled-components';

// Community
const CommunityPage = () => {
  return (
    <div>
      CommunityPage 입니다
      <Sticker1>
        ffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdaf
      </Sticker1>
      <Sticker2>
        ffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdafffsadfsdafsdafsadfsdaf
      </Sticker2>
    </div>
  );
};

const Sticker = styled.div`
  position: absolute;
  top: 0px;
  width: 40%;
  overflow: scroll;
  height: 40%;
  background-color: aliceblue;
`;

const Sticker1 = styled(Sticker)``;
const Sticker2 = styled(Sticker)`
  width: 50%;
  height: 20%;
  top: auto;
  bottom: 2px;
  background-color: red;
`;

export default CommunityPage;
