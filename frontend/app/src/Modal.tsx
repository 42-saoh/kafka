import React, { useState, useEffect } from "react";
import { Row, Col, Button } from "react-bootstrap";
import { ModalBackground, ModalContainer, AnimationDiv } from "./style/ModalStyle";
import { LoadingAnimation } from "./LoadingAnimation";
import { Get } from "./axios/axios";

interface ModalProps {
  closeModal: () => void;
}

export const Modal = ({closeModal} : ModalProps) => {
  const [loading, setLoading] = useState(true);
  const [userID, setUserID] = useState<string>('');

  const getAxios = async () => {
    const response = await Get(
      '/server4/end',
    );
    return response;
  }

  const getResult = async () => {
    const response = await Get(
      '/server4/hotdeal',
      {
        params: {
            userID: userID
        }
      }
    );
    return response;
  }

  const handleChange = (event : any) => {
    setUserID(event.target.value);
  }

  const handleClick = () => {
    getResult().then((res) => {
      alert("당첨 되셨습니다!");
    }).catch((err) => {
      alert("꽝 다음 기회에!");
    })
  }

  useEffect(() => {
    getAxios().then((res) => {
        setLoading(false);
      }).catch((err) => {
        setTimeout(getAxios, 3000);
      })
  }, []);

  return (
    <ModalBackground>
        <ModalContainer onClick={(e) => e.stopPropagation()}>
        {loading ? (
          <LoadingAnimation />
          ) : (
          <>
            <Row>
              <AnimationDiv>
                🏆 결 과 확 인! 🏆
              </AnimationDiv>
            </Row>
            <Row>
              <input 
                  type="text" 
                  value={userID} 
                  onChange={handleChange}
                  placeholder="Enter your userID"
              />
              <Col> <Button variant="success" className="w-100 mb-2" onClick={handleClick}>결과 확인</Button> </Col>
              <Col> <Button variant="success" className="w-100 mb-2" onClick={closeModal}>나가기</Button> </Col> 
            </Row>
          </>
        )}
        </ModalContainer>
    </ModalBackground>
  );
};