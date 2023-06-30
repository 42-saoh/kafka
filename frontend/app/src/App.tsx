import React, { useState } from 'react';
import { Container, Overlay, OverlayPanel, GhostButton, Wrapper } from './style/AppStyle';
import { Get } from './axios/axios';
import { Modal } from './Modal';

export const App = () => {
  const [showModal, setShowModal] = useState<boolean>(false);

  const closeModal = () => {
    setShowModal(false);
  }

  const getAxios = async () => {
    const response = await Get(
      '/server1/hotdeal',
    );
    return response;
  }

  const handleClick = () => {
    if (showModal) {
      return ;
    }
    getAxios().then((res) => {
      if (res.status === 200) {
        setShowModal(true);
      } else {
        alert(res.status + ' ' + res.statusText)
      }
    }).catch((err) => {
      alert(err);
    })
  }

  return (
    <Wrapper>
      <Container>
        <Overlay>
          <OverlayPanel>
            <h1> 핫 딜 놓 치 지 마 세 요.</h1>
              <GhostButton onClick={handleClick}> 구매 </GhostButton>
          </OverlayPanel>
        </Overlay>
        <div>
          {showModal && (
                  <Modal
                      closeModal={closeModal}
                  />
              )}
        </div>
      </Container>
    </Wrapper>
  );
}