package listeners;

import shared.AttackSummary;

public interface IAttackListener {
	public void onAttackPerformed(AttackSummary args);
}
