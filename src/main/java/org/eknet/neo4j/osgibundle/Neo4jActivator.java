/*

    Copyright (c) 2002-2012 "Neo Technology,"
    Network Engine for Objects in Lund AB [http://neotechnology.com]

    This file is part of Neo4j.

    Neo4j is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/
/*
    Modified by Eike Kettner <eike.kettner@gmail.com>

 */

package org.eknet.neo4j.osgibundle;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.IndexProvider;
import org.neo4j.index.lucene.LuceneIndexProvider;
import org.neo4j.kernel.ListIndexIterable;
import org.neo4j.kernel.impl.cache.CacheProvider;
import org.neo4j.kernel.impl.cache.SoftCacheProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 11.12.12 11:58
 */
public class Neo4jActivator implements BundleActivator {

    private ServiceRegistration factoryRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Install neo4j GraphDatabaseFactory.");
        List<CacheProvider> cacheProvider = new LinkedList<CacheProvider>();
        cacheProvider.add(new SoftCacheProvider());

        List<IndexProvider> indexProvider = new LinkedList<IndexProvider>();
        indexProvider.add(new LuceneIndexProvider());

        GraphDatabaseFactory gf = new GraphDatabaseFactory();
        gf.setCacheProviders(cacheProvider);
        ListIndexIterable indexList = new ListIndexIterable();
        indexList.setIndexProviders(indexProvider);

        gf.setIndexProviders(indexList);

        factoryRegistration = context.registerService(
            GraphDatabaseFactory.class.getName(),
            gf,
            new Hashtable()
        );
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (factoryRegistration != null) {
            factoryRegistration.unregister();
        }
    }
}
