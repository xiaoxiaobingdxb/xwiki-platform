/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.wikistream.internal.job;

import org.xwiki.job.internal.AbstractJob;
import org.xwiki.job.internal.DefaultJobStatus;
import org.xwiki.wikistream.input.InputWikiStream;
import org.xwiki.wikistream.input.InputWikiStreamFactory;
import org.xwiki.wikistream.job.WikiStreamConverterJobRequest;
import org.xwiki.wikistream.output.OutputWikiStream;
import org.xwiki.wikistream.output.OutputWikiStreamFactory;

/**
 * Perform a WikiStream conversion.
 * 
 * @version $Id$
 * @since 5.3M2
 */
public class WikiStreamConverterJob extends
    AbstractJob<WikiStreamConverterJobRequest, DefaultJobStatus<WikiStreamConverterJobRequest>>
{
    /**
     * The id of the job.
     */
    public static final String JOBTYPE = "wikistream.converter";

    @Override
    public String getType()
    {
        return JOBTYPE;
    }

    @Override
    protected void runInternal() throws Exception
    {
        InputWikiStreamFactory inputFactory =
            this.componentManager.getInstance(InputWikiStreamFactory.class, getRequest().getInputType().serialize());

        InputWikiStream inputWikiStream = inputFactory.createInputWikiStream(getRequest().getInputProperties());

        OutputWikiStreamFactory outputFactory =
            this.componentManager.getInstance(OutputWikiStreamFactory.class, getRequest().getOutputType().serialize());

        OutputWikiStream outputWikiStream = outputFactory.createOutputWikiStream(getRequest().getOutputProperties());

        // Convert

        inputWikiStream.read(outputWikiStream.getFilter());
    }
}
