import React from 'react';
import { requireNativeComponent } from 'react-native';

interface SDCViewProps {
  height: number;
  width: number;
  onDone(uri: string): void;
  onFail?(): void;
  onCaptureStatusChange?(status: string): void;

}

const NativeComponent: any = requireNativeComponent('RCTAu10tixRNSDCView');

class SDCView extends React.Component<SDCViewProps> {

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
        onDone={(event: any) => this.props.onDone(event.nativeEvent.uri)}
        onFail={() => {
          this.props.onFail && this.props.onFail();
        }}
        onCaptureStatusChange={this._onCaptureStatusChange}
      />
    );
  }
}

export default SDCView;
