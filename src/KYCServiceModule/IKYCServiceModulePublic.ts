export default interface IKYCServiceModulePublic {
  prepare(token: string): Promise<boolean>;
  startSDCSession(): Promise<string | undefined>;
  startLivenessSession(): Promise<string | undefined>;
  cleanSession(): void;
  getSDKVersion(): void;
}