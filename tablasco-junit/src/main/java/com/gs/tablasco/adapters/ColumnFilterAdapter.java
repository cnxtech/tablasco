/*
 * Copyright 2017 Goldman Sachs.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.gs.tablasco.adapters;

import com.gs.tablasco.VerifiableTable;
import com.gs.tablasco.verify.DefaultVerifiableTableAdapter;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

class ColumnFilterAdapter extends DefaultVerifiableTableAdapter
{
    private final IntArrayList indexMap;

    ColumnFilterAdapter(VerifiableTable delegate, Predicate<String> columnFilter)
    {
        super(delegate);
        this.indexMap = new IntArrayList(delegate.getColumnCount());
        for (int i = 0; i < delegate.getColumnCount(); i++)
        {
            if (columnFilter.accept(delegate.getColumnName(i)))
            {
                indexMap.add(i);
            }
        }
    }

    @Override
    public int getColumnCount()
    {
        return this.indexMap.size();
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        return super.getColumnName(this.indexMap.get(columnIndex));
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return super.getValueAt(rowIndex, this.indexMap.get(columnIndex));
    }
}
