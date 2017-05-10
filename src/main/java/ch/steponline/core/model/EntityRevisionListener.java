package ch.steponline.core.model;

import org.hibernate.envers.RevisionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 16:10
 */
public class EntityRevisionListener implements RevisionListener {

    public void newRevision(Object revisionEntity) {
        EntityRevison entityRevision = (EntityRevison)revisionEntity;
        String userName = null;
        //TODO: get the user who is connectes

        entityRevision.setUsername(userName);
    }
}
