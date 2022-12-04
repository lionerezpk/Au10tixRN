import React from 'react';
import { requireNativeComponent } from 'react-native';

const NativeComponent: any = requireNativeComponent('RCTAu10tixLivenessView');

interface LivenessViewProps {
  height: number;
  width: number;
  onDone(uri: string): void;
  onFail?(): void;
  onCaptureStatusChange?(status: string): void;
}

class LivenessView extends React.Component<LivenessViewProps> {

  private readonly _onDone = (event: any) => {
    const nativeEvent = event.nativeEvent;
    this.props.onDone(nativeEvent.uri);
  }

  private readonly _onCaptureStatusChange = (event: any) => {
    const nativeEvent = event.nativeEvent;
    this.props.onCaptureStatusChange && this.props.onCaptureStatusChange(nativeEvent.status);
  }

  render() {
    return (
      <NativeComponent
        style={{
          width: this.props.width,
          height: this.props.height,
        }}
        onDone={this._onDone}
        onFail={() => this.props.onFail && this.props.onFail()}
        onCaptureStatusChange={this._onCaptureStatusChange}
      />
    );
  }
}

export default LivenessView;
