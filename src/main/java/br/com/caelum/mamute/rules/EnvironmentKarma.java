package br.com.caelum.mamute.rules;

public class EnvironmentKarma {

	private Environment environment;

	@Inject
	public EnvironmentKarma(Environment environment) {
		this.environment = environment;
	}

	public long get(PermissionRules rule) {
		String accessLevelString = environment.get("permission.rule." + rule.getPermissionName());
		long karma = Long.parseLong(accessLevelString);
		return karma;
	}
}
