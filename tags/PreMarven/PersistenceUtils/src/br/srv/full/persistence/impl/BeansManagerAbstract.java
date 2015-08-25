/**
 * 
 */
package br.srv.full.persistence.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.srv.full.persistence.BeansManager;

/**
 * @author Delfino
 * 
 */
public abstract class BeansManagerAbstract implements BeansManager {

	protected final Log log = LogFactory.getLog(getClass());

	protected EntityManagerFactory emf;

	abstract protected EntityManagerFactory getEmf();

	protected void setEmf(EntityManagerFactory p_emf) {
		emf = p_emf;
	}

	{

		log.debug(emf);
	}

	/**
	 * @param p_em
	 */
	protected void rollBackTransaction(EntityManager p_em) {
		p_em.getTransaction().rollback();
	}

	/**
	 * @param l_em
	 */
	protected void closeEntityManager(EntityManager l_em, boolean... p_tx) {

		if (p_tx != null && p_tx.length >= 1 && p_tx[0] && l_em.getTransaction().isActive())
			manageTransactionClossing(l_em);

		l_em.close();
	}

	/**
	 * @param p_em
	 */
	protected void manageTransactionClossing(EntityManager p_em) {
		p_em.getTransaction().commit();
	}

	/**
	 * @return
	 */
	protected EntityManager createEntityManager(boolean... p_tx) {
		EntityManager l_em = emf.createEntityManager();
		if (p_tx != null && p_tx.length >= 1 && p_tx[0])
			managerTransaction(l_em);

		return l_em;
	}

	/**
	 * @param p_em
	 */
	protected void managerTransaction(EntityManager p_em) {
		p_em.getTransaction().begin();
	}
}
