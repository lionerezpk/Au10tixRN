export default interface IKYCServiceModulePrivate {
  setJWT(jwt: string): void;
  prepare(): Promise<boolean>;
  startSDCSession(): Promise<string | undefined>;
  startLivenessSession(): Promise<string>;
  cleanSession(): void;
  getSDKVersion(): void;
}